package com.github.lex090.cryptoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var serviceGenerator: IRemoteNetworkServiceGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()


    }

    private fun injectDependencies() {
        val component = (applicationContext as CryptoAppApplication)
            .applicationComponent

        component.inject(this)
    }
}