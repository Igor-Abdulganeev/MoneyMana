package ru.gorinih.moneymana.presentation.ui.categorydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.databinding.FragmentCategoryDetailsBinding
import ru.gorinih.moneymana.presentation.ui.categorydetails.adapter.CategoryDetailsAdapter
import ru.gorinih.moneymana.presentation.ui.categorydetails.viewmodel.CategoryDetailsViewModel
import ru.gorinih.moneymana.presentation.ui.categorydetails.viewmodel.CategoryDetailsViewModelFactory

class CategoryDetails private constructor() : Fragment() {
    private lateinit var _binding: FragmentCategoryDetailsBinding
    private val binding get() = _binding

    private val checkViewModel: CategoryDetailsViewModel by viewModels {
        CategoryDetailsViewModelFactory(requireContext())
    }

    private lateinit var adapter: CategoryDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCategoryDetailsBinding.inflate(inflater, container, false)
        .run {
            _binding = this
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val idCategory = requireNotNull(arguments?.getLong("CategoryDetails"))
        adapter = CategoryDetailsAdapter()
        binding.categoriesRecyclerview.adapter = adapter
        lifecycleScope.launch {
            checkViewModel.listChecks.collect {
                adapter.submitList(it)
            }
        }
        getData(idCategory)
    }

    private fun getData(idCategory: Long) {
        lifecycleScope.launch {
            val jobCheck = async {
                checkViewModel.loadChecks(idCategory)
            }
            val jobCategory = async {
                checkViewModel.getCategoryProperty(idCategory)
            }
            jobCategory.await().let {
                it.collect {
                    with(binding) {
                        nameCategoryTextview.text = it.title_category
                        categoryImageview.setImageResource(it.image_category)
                        sumTextview.text =
                            resources.getString(R.string.sum_spent, it.sum_check ?: 0)
                    }
                }
            }
            jobCheck.await()
        }
    }

    companion object {
        fun newInstance(itemId: Long): CategoryDetails {
            return CategoryDetails().apply {
                arguments = Bundle().apply {
                    this.putLong("CategoryDetails", itemId)
                }
            }
        }
    }
}