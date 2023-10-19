package com.example.homerecipes.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.homerecipes.databinding.FragmentDialogEditTextBinding
import java.lang.IllegalStateException

class DialogEditTextFragment: DialogFragment() {
    private lateinit var binding: FragmentDialogEditTextBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleText = arguments?.getString(TITLE_TEXT) ?:
            throw IllegalArgumentException("Whoops, I need a title!")
        val placeholderText = arguments?.getString(PLACEHOLDER) ?:
            throw IllegalArgumentException("Whoops, I need a placeholder!")
        val dialogId = arguments?.getString(DIALOG_ID)

        return activity?.let {
            binding = FragmentDialogEditTextBinding.inflate(
                requireActivity().layoutInflater
            ).apply {
                etEditText.hint = placeholderText
                tvTitle.text = titleText
            }

            AlertDialog.Builder(it)
                .setView(binding.root)
                .setPositiveButton("Confirm") { _, _ ->
                    setFragmentResult(
                        FRAGMENT_RESULT,
                        bundleOf(
                            EDIT_TEXT_VALUE to binding.etEditText.text.toString(),
                            DIALOG_ID to dialogId
                        )
                    )
                }.setNegativeButton("Cancel") { _, _ ->
                    dismiss()
                }.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }

    companion object {
        const val TITLE_TEXT = "TITLE_TEXT"
        const val PLACEHOLDER = "PLACEHOLDER"
        const val DIALOG_ID = "DIALOG_ID"

        const val FRAGMENT_RESULT = "FRAGMENT_RESULT"
        const val EDIT_TEXT_VALUE = "EDIT_TEXT_VALUE"

        const val INGREDIENT_DIALOG = "INGREDIENT_DIALOG"
        const val PREPARE_MODE_DIALOG = "PREPARE_MODE_DIALOG"

        fun show(
            title: String,
            placeholder: String,
            fragmentManager: FragmentManager,
            tag: String = DialogEditTextFragment::class.simpleName.toString(),
            dialogId: String? = null
        ) {
            DialogEditTextFragment().apply {
                arguments = bundleOf(
                    TITLE_TEXT to title,
                    PLACEHOLDER to placeholder,
                    DIALOG_ID to dialogId
                )
            }.show(fragmentManager, tag)
        }
    }
}