package com.example.homerecipes.presentation.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homerecipes.R
import com.example.homerecipes.databinding.FragmentRecipeBinding
import com.example.homerecipes.presentation.dialog.DialogEditTextFragment
import com.example.homerecipes.presentation.dialog.DialogEditTextFragment.Companion.FRAGMENT_RESULT
import com.example.homerecipes.presentation.recipe.adapter.RecipeAdapter
import com.example.homerecipes.presentation.recipe.viewmodel.RecipeState
import com.example.homerecipes.presentation.recipe.viewmodel.RecipeViewModel

class RecipeFragment : Fragment() {
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.Factory()
    }
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { RecipeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupAdapter()
        observeStates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.fabRecipe.setOnClickListener {
            showDialog()
        }

        setFragmentResultListener(FRAGMENT_RESULT) { requestKey, bundle ->
            val recipeName = bundle.getString(DialogEditTextFragment.EDIT_TEXT_VALUE) ?: ""
            viewModel.insert(recipeName)
        }

        adapter.click = { recipeItem ->
            val action = RecipeFragmentDirections.actionNavRecipeToNavDetail(
                recipeItem.id
            )
            findNavController().navigate(action)
        }
    }

    private fun setupAdapter() {
        binding.rvRecipes.adapter = adapter
    }

    // This is the ViewModel's "state-machine" usage, associated with the View layer
    private fun observeStates() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                RecipeState.Loading -> {
                    binding.pbLoading.isVisible = true
                }

                RecipeState.Empty -> {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.label_empty_recipes),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is RecipeState.Error -> {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is RecipeState.Success -> {
                    binding.pbLoading.isVisible = false
                    adapter.submitList(state.recipe)
                }
            }
        }
    }

    private fun showDialog() {
        DialogEditTextFragment.show(
            title = getString(R.string.title_new_recipe),
            placeholder = getString(R.string.label_name_recipe),
            fragmentManager = parentFragmentManager
        )
    }
}