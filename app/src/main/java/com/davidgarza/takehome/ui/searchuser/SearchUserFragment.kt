package com.davidgarza.takehome.ui.searchuser

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.davidgarza.takehome.R
import com.davidgarza.takehome.databinding.FragmentSearchUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment : Fragment() {
    private val viewModel: SearchUserViewModel by viewModels()

    private lateinit var binding: FragmentSearchUserBinding
    private lateinit var reposAdapter: SearchRepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_user, container, false)
        binding = FragmentSearchUserBinding.bind(view)

        setupUI()
        setupRecyclerView()
        setupObservers()

        return view
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.apply {
                    viewModel.uiState.collect { state ->
                        ivUserProfile.y += 50f
                        tvUserFullname.y += 50f
                        rvRepos.y += 50f
                        resultsGroup.alpha = 0f

                        when (state) {
                            is SearchUserViewModel.ViewState.Data -> {
                                Glide.with(requireContext())
                                    .load(state.data?.userDetails?.avatarUrl)
                                    .listener(object : RequestListener<Drawable> {
                                        override fun onLoadFailed(
                                            e: GlideException?,
                                            model: Any?,
                                            target: Target<Drawable>?,
                                            isFirstResource: Boolean
                                        ): Boolean {
                                            return false
                                        }

                                        override fun onResourceReady(
                                            resource: Drawable?,
                                            model: Any?,
                                            target: Target<Drawable>?,
                                            dataSource: DataSource?,
                                            isFirstResource: Boolean
                                        ): Boolean {
                                            fadeInAnimation()
                                            return false
                                        }
                                    }).transition(DrawableTransitionOptions.withCrossFade())
                                    .into(ivUserProfile)
                                tvUserFullname.text = state.data?.userDetails?.name
                                reposAdapter.submitList(state.data?.repoList)

                            }

                            is SearchUserViewModel.ViewState.Error -> Toast.makeText(
                                requireActivity(), state.error, Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        reposAdapter = SearchRepoAdapter {
            val action = SearchUserFragmentDirections.actionSearchFragmentToDetailsFragment(
                it, viewModel.totalForks
            )
            findNavController().navigate(action)
        }
        binding.rvRepos.adapter = reposAdapter
    }

    private fun setupUI() {
        binding.apply {
            //Edit text listener
            tietSearchUsername.apply {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.setUsernameQuery(query.toString())
                    }

                    override fun afterTextChanged(query: Editable?) {}
                })
                imeOptions = EditorInfo.IME_ACTION_SEARCH
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.fetchUserDetailsRepos()
                        hideKeyboard()
                    }
                    true
                }
            }

            //search button click listener
            buttonSearch.setOnClickListener {
                viewModel.fetchUserDetailsRepos()
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val inputManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun fadeInAnimation() {
        binding.apply {
            resultsGroup.visibility = View.VISIBLE

            ivUserProfile.animate().alpha(1f).translationYBy(-50f).duration = 1000

            tvUserFullname.animate().alpha(1f).translationYBy(-50f).duration = 3000

            rvRepos.animate().alpha(1f).translationYBy(-50f).duration = 6000
        }
    }
}