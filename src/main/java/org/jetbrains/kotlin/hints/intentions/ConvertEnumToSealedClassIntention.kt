/*******************************************************************************
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *******************************************************************************/
package org.jetbrains.kotlin.hints.intentions

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.analyzer.AnalysisResult
import javax.swing.text.Document
import org.jetbrains.kotlin.psi.psiUtil.getNonStrictParentOfType
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.reformatting.format
import org.jetbrains.kotlin.hints.atomicChange

class ConvertEnumToSealedClassIntention(doc: Document,
                                        analysisResult: AnalysisResult?,
                                        psi: PsiElement) : ApplicableIntention(doc, analysisResult, psi) {

    private var expression: KtClass? = null

    override fun isApplicable(caretOffset: Int): Boolean {
        expression = psi.getNonStrictParentOfType(KtClass::class.java) ?: return false
        val element = expression ?: return false
        element.nameIdentifier ?: return false
        element.modifierList?.getModifier(KtTokens.ENUM_KEYWORD) ?: return false

        return true
    }

    override fun getDescription() = "Convert to sealed class"

    private fun KtClass.generateSealedClass(): String {
        val builder = StringBuilder()
        
        with (builder) {
            append("sealed class $name")

            val constructor = primaryConstructor?.text ?: ""

            append(constructor).append(" {\n")

            for (member in declarations) {
                if (member !is KtEnumEntry) continue

                append("object ${member.name} :")

                val initializers = member.initializerList?.initializers ?: emptyList()
                if (initializers.isNotEmpty()) {
                    append(initializers.joinToString { "$name${it.text}" })
                } else append("$name()")

                member.getBody()?.let { body -> append(body.text) }
                append("\n")
            }

            getBody()?.let { body -> 
                body.declarations
                        .filter { it !is KtEnumEntry }
                        .forEach { append(it.text) }
            }

            append("}")
        }
        return builder.toString()
    }
    
    override fun implement() {
        val element = expression ?: return

        val newText = element.generateSealedClass()
        
        val startOffset = element.textRange.startOffset
        val lengthToDelete = element.textLength
        
        doc.atomicChange { 
            remove(startOffset, lengthToDelete)
            insertString(startOffset, newText, null)
            format(this, element.textRange.startOffset)
        }
    }
}