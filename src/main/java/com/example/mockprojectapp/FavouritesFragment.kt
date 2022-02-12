package com.example.mockprojectapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.mockprojectapp.databinding.FragmentFavouritesBinding
import com.example.mockprojectapp.db.ProjectEntity
import com.example.mockprojectapp.model.MainApp
import com.example.mockprojectapp.model.ProjectViewModel
import com.example.mockprojectapp.model.ProjectViewModelFactory
import com.example.mockprojectapp.recycler.FavouritesAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FavouritesFragment : Fragment() {

    private val viewModel: ProjectViewModel by activityViewModels {
        ProjectViewModelFactory(
            (activity?.application as MainApp).database.projectDao()
        )
    }

    private var _binding: FragmentFavouritesBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            val db = (requireActivity().application as MainApp).database
            db.projectDao().retreiveAll().collect { data ->
                val adapter = FavouritesAdapter(data) { position ->
                    val user = data[position]
                    showFinalScoreDialog(user)
                }
                binding.recyclerView.adapter = adapter
            }
        }

        binding.deleteAll.setOnClickListener {
            val db = (requireActivity().application as MainApp).database
            db.projectDao().deleteAll()
        }
    }

    private fun showFinalScoreDialog(entity: ProjectEntity) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Hey!")
                .setMessage("Do you want to delete this user?")
                .setCancelable(false)
                .setNegativeButton("Delete User") { _, _ ->
                    delete(entity)
                }
                .setPositiveButton("cancel") { _, _ ->
                    cancel()
                }
                .show()
        }
    }

    private fun delete(entity: ProjectEntity) {
        val db = (requireActivity().application as MainApp).database
        db.projectDao().deleteItem(entity)
    }

    private fun cancel() {
    }


}