package com.example.mockprojectapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mockprojectapp.databinding.FragmentDetailsBinding
import com.example.mockprojectapp.model.MainApp
import com.example.mockprojectapp.model.ProjectViewModel
import com.example.mockprojectapp.model.ProjectViewModelFactory


class DetailsFragment : Fragment() {
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: ProjectViewModel by activityViewModels {
        ProjectViewModelFactory(
            (activity?.application as MainApp).database.projectDao()
        )
    }

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileImage.load(args.currentDev.image) {
            error(R.drawable.ic_baseline_account_box_24)
            placeholder(R.drawable.ic_baseline_account_box_24)
        }
        binding.loginRes.text = args.currentDev.login
        binding.idRes.text = args.currentDev.id.toString()
        binding.reposRes.text = args.currentDev.reposUrl

        binding.add.setOnClickListener {
            viewModel.addNewItem(
                binding.loginRes.text.toString(),
                binding.idRes.text.toString().toInt(),
                args.currentDev.image,
                args.currentDev.reposUrl
            )

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}