package com.davidgarza.takehome.ui.repodetails

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.davidgarza.takehome.R
import com.davidgarza.takehome.databinding.FragmentRepoDetailsBinding
import com.davidgarza.takehome.databinding.FragmentSearchUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentRepoDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repo_details, container, false)
        binding = FragmentRepoDetailsBinding.bind(view)

        setupUI()

        return view
    }

    private fun setupUI() {
        val repository = args.repository
        val totalForks = args.totalForks

        binding.apply {
            tvRepoId.text = getString(R.string.repo_id, repository.idRepo)
            tvRepoName.text = getString(R.string.repo_name, repository.name)
            tvRepoDescription.text = getString(R.string.repo_description, repository.description)
            tvRepoUpdatedAt.text = getString(R.string.repo_last_update, repository.updated_at)
            tvRepoStargazersCount.text = getString(R.string.repo_stars, repository.stargazers_count)
            tvRepoForksCount.text = getString(R.string.repo_forks, repository.stargazers_count)
            if (totalForks >= 5000)
                llMainContainer.forEach {
                    (it as TextView).setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gold
                        )
                    )
                }
        }
    }
}