package com.example.homerecipes.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.homerecipes.databinding.FragmentDetailBinding
import com.example.homerecipes.presentation.detail.adapter.ItemListAdapter
import com.example.homerecipes.presentation.detail.viewmodel.DetailState
import com.example.homerecipes.presentation.detail.viewmodel.DetailViewModel
import com.example.homerecipes.presentation.dialog.DialogEditItemListFragment
import com.example.homerecipes.presentation.dialog.DialogEditItemListFragment.Companion.EDIT_FRAGMENT_RESULT
import com.example.homerecipes.presentation.dialog.DialogEditTextFragment
import com.example.homerecipes.presentation.dialog.DialogEditTextFragment.Companion.FRAGMENT_RESULT
import com.example.homerecipes.presentation.mapper.toModelIngredient
import com.example.homerecipes.presentation.mapper.toModelPrepareMode

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.Factory(args.recipeId)
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val ingredientsAdapter: ItemListAdapter by lazy { ItemListAdapter() }
    private val prepareModesAdapter: ItemListAdapter by lazy { ItemListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupAdapters()
        observeStates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.ivBtnAddIngredient.setOnClickListener {
            showInsertDialog("Ingredient", DialogEditTextFragment.INGREDIENT_DIALOG)
        }

        binding.ivBtnAddPrepareMode.setOnClickListener {
            showInsertDialog("Prepare Mode", DialogEditTextFragment.PREPARE_MODE_DIALOG)
        }

        setFragmentResultListener(FRAGMENT_RESULT) { requestKey, bundle ->
            val name = bundle.getString(DialogEditTextFragment.EDIT_TEXT_VALUE) ?: ""
            val dialogId = bundle.getString(DialogEditTextFragment.DIALOG_ID)

            if (dialogId == DialogEditTextFragment.INGREDIENT_DIALOG)
                viewModel.insertIngredient(args.recipeId, name)
            else if (dialogId == DialogEditTextFragment.PREPARE_MODE_DIALOG)
                viewModel.insertPrepareMode(args.recipeId, name)
        }

        ingredientsAdapter.click = {
            showEditOrDeleteDialog(
                "Ingredient",
                DialogEditItemListFragment.INGREDIENT_DIALOG,
                it.id,
                it.name
            )
        }

        prepareModesAdapter.click = {
            showEditOrDeleteDialog(
                "Prepare Mode",
                DialogEditItemListFragment.PREPARE_MODE_DIALOG,
                it.id,
                it.name
            )
        }

        setFragmentResultListener(EDIT_FRAGMENT_RESULT) { requestKey, bundle ->
            val name = bundle.getString(DialogEditItemListFragment.EDIT_TEXT_VALUE) ?: ""
            val dialogId = bundle.getString(DialogEditItemListFragment.DIALOG_ID)
            val itemId = bundle.getInt(DialogEditItemListFragment.ITEM_ID)
            val isToDelete = bundle.getBoolean(DialogEditItemListFragment.IS_TO_DELETE)

            if (dialogId == DialogEditItemListFragment.INGREDIENT_DIALOG) {
                if (isToDelete)
                    viewModel.deleteIngredient(args.recipeId, name, itemId)
                else
                    viewModel.updateIngredient(args.recipeId, name, itemId)
            } else if (dialogId == DialogEditItemListFragment.PREPARE_MODE_DIALOG) {
                if (isToDelete)
                    viewModel.deletePrepareMode(args.recipeId, name, itemId)
                else
                    viewModel.updatePrepareMode(args.recipeId, name, itemId)
            }
        }
    }

    private fun setupAdapters() {
        binding.rvIngredients.adapter = ingredientsAdapter
        binding.rvPrepareMode.adapter = prepareModesAdapter
    }

    private fun observeStates() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailState.Loading -> {
                    binding.pbIngredientsLoading.isVisible = true
                    binding.pbPrepareModeLoading.isVisible = true
                }

                is DetailState.Error -> {
                    binding.pbIngredientsLoading.isVisible = false
                    binding.pbPrepareModeLoading.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is DetailState.Success -> {
                    (requireActivity() as AppCompatActivity).supportActionBar?.title =
                        state.fullRecipe.recipe.name
                    binding.pbIngredientsLoading.isVisible = false
                    binding.pbPrepareModeLoading.isVisible = false
                    ingredientsAdapter.submitList(state.fullRecipe.ingredient.toModelIngredient())
                    prepareModesAdapter.submitList(state.fullRecipe.prepareMode.toModelPrepareMode())
                    binding.rvIngredients.isVisible = true
                    binding.rvPrepareMode.isVisible = true
                }
            }
        }
    }

    private fun showInsertDialog(name: String, dialogId: String) {
        DialogEditTextFragment.show(
            title = "New $name",
            placeholder = name,
            fragmentManager = parentFragmentManager,
            dialogId = dialogId
        )
    }

    private fun showEditOrDeleteDialog(
        name: String,
        dialogId: String,
        itemId: Int,
        itemName: String
    ) {
        DialogEditItemListFragment.show(
            title = "Edit or delete $name",
            placeholder = name,
            fragmentManager = parentFragmentManager,
            dialogId = dialogId,
            itemId = itemId,
            itemName = itemName
        )
    }
}