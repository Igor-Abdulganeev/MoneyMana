package ru.gorinih.moneymana.ui.categories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gorinih.moneymana.databinding.FragmentManaCategoriesBinding
import ru.gorinih.moneymana.ui.NavigationActivity

class ManaCategoriesFragment() : Fragment() {
    private lateinit var _binding: FragmentManaCategoriesBinding
    private val binding get() = _binding

    private lateinit var barView: NavigationActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentManaCategoriesBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barView.setBarVisibility(View.VISIBLE)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        barView = context as NavigationActivity
    }

    companion object {
        fun newInstance() = ManaCategoriesFragment()
    }
}