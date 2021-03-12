package com.example.learningassistant.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.learningassistant.R
import com.example.learningassistant.database.rateUser
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast
import kotlinx.android.synthetic.main.fragment_rating.*


class RatingFragment(private var user: User, private var menuRating: Menu) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rating, container, false)
    }

    override fun onResume() {
        super.onResume()
        btn_rate.setOnClickListener {
            rateUser(user,ratingBar.rating){
                showToast("Спасибо за оценку!")
                menuRating.clear()
               //скрыть меню
                dismiss()
            }
        }
    }

}