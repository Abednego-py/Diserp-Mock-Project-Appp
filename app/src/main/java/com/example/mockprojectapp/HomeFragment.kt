package com.example.mockprojectapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mockprojectapp.databinding.FragmentHomeBinding
import com.example.mockprojectapp.db.ProjectEntity
import com.example.mockprojectapp.model.MainApp
import com.example.mockprojectapp.model.ProjectViewModel
import com.example.mockprojectapp.model.ProjectViewModelFactory
import com.example.mockprojectapp.recycler.ProjectAdapter
import com.example.mockprojectapp.retrofit.Service
import com.example.mockprojectapp.ui.UserObject
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private val viewModel: ProjectViewModel by activityViewModels {
        ProjectViewModelFactory(
            (activity?.application as MainApp).database.projectDao()
        )
    }
    lateinit var item: ProjectEntity
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = builder.create(Service::class.java)

        binding.progressBar.visibility = View.VISIBLE
        binding.retry.visibility = View.GONE
        lifecycleScope.launch {
            try {
                val response = service.listUsers()
                binding.progressBar.visibility = View.GONE

                val adapter = ProjectAdapter(response) { position ->
                    val currentUser = response.items[position]
                    val currentDev = UserObject(
                        currentUser.avatar_url,
                        currentUser.login, currentUser.id, currentUser.repos_url
                    )

                    val action =
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(currentDev)
                    findNavController().navigate(action)
                }
                binding.recyclerView.adapter = adapter
            } catch (e: Exception) {

                binding.progressBar.visibility = View.GONE
                binding.retry.visibility = View.VISIBLE

                Toast.makeText(context, "An error occured", Toast.LENGTH_SHORT).show()
                binding.retry.setOnClickListener {

                    binding.progressBar.visibility = View.VISIBLE
                    lifecycleScope.launch {

                        try {
                            val response = service.listUsers()
                            binding.progressBar.visibility = View.GONE

                            val adapter = ProjectAdapter(response) { position ->
                                val currentUser = response.items[position]
                                val currentDev = UserObject(
                                    currentUser.avatar_url,
                                    currentUser.login, currentUser.id, currentUser.repos_url
                                )

                                val action =
                                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                                        currentDev
                                    )
                                findNavController().navigate(action)
                            }
                            binding.recyclerView.adapter = adapter
                        } catch (e: Exception) {
                            Toast.makeText(context, "An error occured", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}