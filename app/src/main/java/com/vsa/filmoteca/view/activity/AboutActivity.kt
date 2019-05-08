package com.vsa.filmoteca.view.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import com.vsa.filmoteca.R
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutActivity : AppCompatActivity(), View.OnClickListener {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        ButterKnife.bind(this)
        wrapperContent.setOnClickListener(this)
        try {
            val packageManager = this.packageManager
            val packageInfo = packageManager.getPackageInfo(this.packageName, PackageManager.GET_META_DATA)
            textViewAboutUsBuild.text = String.format(getString(R.string.version_code), packageInfo.versionCode)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun onClick(v: View) {
        if (v !== wrapperAboutUsDialog)
            finish()
    }

}
