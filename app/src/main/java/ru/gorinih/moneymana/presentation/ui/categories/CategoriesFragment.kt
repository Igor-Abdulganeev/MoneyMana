package ru.gorinih.moneymana.presentation.ui.categories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.InternalCoroutinesApi
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.databinding.FragmentManaCategoriesBinding
import ru.gorinih.moneymana.presentation.NavigationActivity
import ru.gorinih.moneymana.presentation.ui.categories.adapter.CategoriesAdapter
import ru.gorinih.moneymana.presentation.ui.categories.viewmodel.CategoriesViewModel
import ru.gorinih.moneymana.presentation.ui.categories.viewmodel.CategoriesViewModelFactory

class CategoriesFragment() : Fragment() {
    private lateinit var _binding: FragmentManaCategoriesBinding
    private val binding get() = _binding

    private val categoriesViewModel: CategoriesViewModel by viewModels {
        CategoriesViewModelFactory(requireContext())
    }
    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var navigation: NavigationActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentManaCategoriesBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation.setBarVisibility(View.VISIBLE)
        categoriesAdapter = CategoriesAdapter()
        categoriesViewModel.manaCategories.observe(viewLifecycleOwner, {
            categoriesAdapter.bindItems(it)
        })
        categoriesViewModel.budget.observe(viewLifecycleOwner, {
            binding.primarySettingsTextView.text =
                getString(R.string.budget_settings, it.sum_budget, it.sum_spent)
        })
        binding.manaRecyclerView.adapter = categoriesAdapter

        binding.primarySettingsButton.setOnClickListener {
            navigation.startSettings()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as NavigationActivity
    }

    @InternalCoroutinesApi
    override fun onResume() {
        super.onResume()
        categoriesViewModel.getActualBudget()
        categoriesViewModel.getUserManaState()
    }

    companion object {
        fun newInstance() = CategoriesFragment()
    }
}