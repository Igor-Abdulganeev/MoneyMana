package ru.gorinih.moneymana.presentation.ui.categoriesmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.gorinih.moneymana.databinding.FragmentCategoriesManagerBinding
import ru.gorinih.moneymana.presentation.ui.categoriesmanager.adapter.CategoriesManagerAdapter
import ru.gorinih.moneymana.presentation.ui.categoriesmanager.viewmodel.CategoriesManagerViewModel
import ru.gorinih.moneymana.presentation.ui.categoriesmanager.viewmodel.CategoriesManagerViewModelFactory

class CategoriesManager : Fragment() {

    private lateinit var _binding: FragmentCategoriesManagerBinding
    private val binding get() = _binding

    private lateinit var catAdapter: CategoriesManagerAdapter

    private val categoriesViewModel: CategoriesManagerViewModel by viewModels {
        CategoriesManagerViewModelFactory(requireContext())
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
        catAdapter = CategoriesManagerAdapter()
        categoriesViewModel.listCategories.observe(viewLifecycleOwner, {
            catAdapter.submitList(it)
        })
        binding.categoryRecyclerview.adapter = catAdapter
    }

    companion object {
        fun newInstance() = CategoriesManager()
    }
}