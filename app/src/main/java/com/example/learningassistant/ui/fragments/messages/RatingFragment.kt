package com.example.learningassistant.ui.fragments.messages

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.learningassistant.R
import com.example.learningassistant.database.rateUser
import com.example.learningassistant.databinding.FragmentRatingBinding
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import kotlinx.android.synthetic.main.fragment_rating.*


class RatingFragment : DialogFragment() {

    private var _binding: FragmentRatingBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(layoutInflater, container, false)
        user = arguments?.getSerializable("User") as User
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.btnRate.setOnClickListener {
            rateUser(user, ratingBar.rating) {
                showToast("Спасибо за оценку!")
                //menuRating.clear()
                APP_ACTIVITY.navController.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}