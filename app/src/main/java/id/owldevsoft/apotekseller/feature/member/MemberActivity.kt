package id.owldevsoft.apotekseller.feature.member

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import id.owldevsoft.apotekseller.BuildConfig
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_member.*
import kotlinx.android.synthetic.main.activity_member.logoapotek
import kotlinx.android.synthetic.main.fragment_profil.*

class MemberActivity : AppCompatActivity() {

    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Member"

        tv_nama.setText(preferenceHelper!!.getUser()!!.data.member[0].fullName)
        tv_email.setText(preferenceHelper!!.getUser()!!.data.member[0].email)
        tv_telp.setText(preferenceHelper!!.getUser()!!.data.member[0].phoneNumber)
        tv_tipe.setText(preferenceHelper!!.getUser()!!.data.member[0].membersType)
        tv_masa_berlaku.setText(preferenceHelper!!.getUser()!!.data.member[0].membersEndDate)

        back.setOnClickListener {
            finish()
        }

    }
}