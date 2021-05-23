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
import ru.gorinih.moneymana.presentation.ui.categories.adapter.ManaCategoriesAdapter
import ru.gorinih.moneymana.presentation.ui.categories.viewmodel.ManaCategoriesViewModel
import ru.gorinih.moneymana.presentation.ui.categories.viewmodel.ManaCategoriesViewModelFactory

class ManaCategoriesFragment() : Fragment() {
    private lateinit var _binding: FragmentManaCategoriesBinding
    private val binding get() = _binding

    private val manaCategoriesViewModel: ManaCategoriesViewModel by viewModels {
        ManaCategoriesViewModelFactory(requireContext())
    }
    private lateinit var manaCategoriesAdapter: ManaCategoriesAdapter

    private lateinit var barView: NavigationActivity

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
        barView.setBarVisibility(View.VISIBLE)
        manaCategoriesAdapter = ManaCategoriesAdapter()
        manaCategoriesViewModel.manaCategories.observe(viewLifecycleOwner, {
            it.forEach { r ->
                println("${r.title_category} - ${r.image_category} - ${r.sum_check}")
            }
            manaCategoriesAdapter.bindItems(it)
        })
/*
        manaCategoriesViewModel.budget.observe(viewLifecycleOwner, {
            binding.primarySettingsTextView.text =
                getString(R.string.budget_settings, it.sumBudget, it.endTime)
        })
*/
        binding.manaRecyclerView.adapter = manaCategoriesAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        barView = context as NavigationActivity
    }

    @InternalCoroutinesApi
    override fun onResume() {
        super.onResume()
        manaCategoriesViewModel.getUserManaState()
//        manaCategoriesViewModel.getBudget()
    }

    companion object {
        fun newInstance() = ManaCategoriesFragment()
    }
}