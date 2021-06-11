package ru.gorinih.moneymana.presentation.ui.categoriesmanager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.databinding.FragmentCategoriesManagerBinding
import ru.gorinih.moneymana.presentation.NavigationActivity
import ru.gorinih.moneymana.presentation.ui.categoriesmanager.adapter.CategoriesManagerAdapter
import ru.gorinih.moneymana.presentation.ui.categoriesmanager.viewmodel.CategoriesManagerViewModel
import ru.gorinih.moneymana.presentation.ui.categoriesmanager.viewmodel.CategoriesManagerViewModelFactory

class CategoriesManager : Fragment() {

    private lateinit var _binding: FragmentCategoriesManagerBinding
    private val binding get() = _binding

    private lateinit var categoryAdapter: CategoriesManagerAdapter

    private lateinit var navigation: NavigationActivity

    private val categoriesViewModel: CategoriesManagerViewModel by viewModels {
        CategoriesManagerViewModelFactory(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as NavigationActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCategoriesManagerBinding.inflate(
        inflater, container, false
    ).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation.setBarVisibility(View.GONE)
        categoryAdapter = CategoriesManagerAdapter {
            lifecycleScope.launch {
                categoriesViewModel.updateCategory(it)
            }
        }

        categoriesViewModel.listCategories.observe(viewLifecycleOwner, {
            categoryAdapter.submitList(it)
        })
        binding.categoryRecyclerview.adapter = categoryAdapter
    }

    companion object {
        fun newInstance() = CategoriesManager()
    }
}