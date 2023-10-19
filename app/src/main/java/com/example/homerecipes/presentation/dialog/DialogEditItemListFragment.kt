package com.example.homerecipes.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.homerecipes.databinding.FragmentDialogEditTextBinding

class DialogEditItemListFragment: DialogFragment() {
    private lateinit var binding: FragmentDialogEditTextBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleText = arguments?.getString(TITLE_TEXT) ?:
            throw IllegalArgumentException("Whoops, I need a title!")
        val placeholderText = arguments?.getString(PLACEHOLDER) ?:
            throw IllegalArgumentException("Whoops, I need a placeholder!")
        val dialogId = arguments?.getString(DIALOG_ID)
        val itemId = arguments?.getInt(ITEM_ID)
        val itemName = arguments?.getString(ITEM_NAME)

        return activity?.let {
            binding = FragmentDialogEditTextBinding.inflate(
                requireActivity().layoutInflater
            ).apply {
                etEditText.hint = placeholderText
                etEditText.setText(itemName)
                tvTitle.text = titleText
            }

            AlertDialog.Builder(it)
                .setView(binding.root)
                .setPositiveButton("Confirm") { _, _ ->
                    setFragmentResult(
                        EDIT_FRAGMENT_RESULT,
                        bundleOf(
                            EDIT_TEXT_VALUE to binding.etEditText.text.toString(),
                            DIALOG_ID to dialogId,
                            ITEM_ID to itemId,
                            IS_TO_DELETE to false
                        )
                    )
                }.setNegativeButton("Delete") { _, _ ->
                    setFragmentResult(
                        EDIT_FRAGMENT_RESULT,
                        bundleOf(
                            EDIT_TEXT_VALUE to binding.etEditText.text.toString(),
                            DIALOG_ID to dialogId,
                            ITEM_ID to itemId,
                            IS_TO_DELETE to true
                        )
                    )
                }.setNeutralButton("Cancel") { _, _ ->
                    dismiss()
                }.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }

    companion object {
        const val EDIT_FRAGMENT_RESULT = "EDIT_FRAGMENT_RESULT"

        const val TITLE_TEXT = "TITLE_TEXT"
        const val PLACEHOLDER = "PLACEHOLDER"
        const val EDIT_TEXT_VALUE = "EDIT_TEXT_VALUE"

        const val DIALOG_ID = "DIALOG_ID"
        const val INGREDIENT_DIALOG = "INGREDIENT_DIALOG"
        const val PREPARE_MODE_DIALOG = "PREPARE_MODE_DIALOG"

        const val ITEM_ID = "ITEM_ID"
        const val ITEM_NAME = "ITEM_NAME"
        const val IS_TO_DELETE = "IS_TO_DELETE"

        fun show(
            title: String,
            placeholder: String,
            fragmentManager: FragmentManager,
            tag: String = DialogEditItemListFragment::class.simpleName.toString(),
            dialogId: String? = null,
            itemId: Int,
            itemName: String
        ) {
            DialogEditItemListFragment().apply {
                arguments = bundleOf(
                    TITLE_TEXT to title,
                    PLACEHOLDER to placeholder,
                    DIALOG_ID to dialogId,
                    ITEM_ID to itemId,
                    ITEM_NAME to itemName
                )
            }.show(fragmentManager, tag)
        }
    }
}