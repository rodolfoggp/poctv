package com.example.poc2

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samsung.multiscreen.Service

class SamsungActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = TVsAdapter(this, ::onClick)
    private val appId = "BFKjejAbbY.GlobosatPlay"
    private val channelId = "com.globo.globoid.auth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_samsung)
    }

    override fun onResume() {
        super.onResume()
        setupLayout()
        discoverTvs()
    }

    private fun setupLayout() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun discoverTvs() {
        val search = Service.search(this)
        search.setOnServiceFoundListener { service ->
            adapter.addItem(service)
        }
        search.start()
    }

    private fun onClick(service: Service) {
        val tvApplication = service.createApplication(appId, channelId)
        tvApplication.setOnConnectListener { client ->
            Toast.makeText(this, "Conectado a $client", Toast.LENGTH_SHORT).show()
        }
    }
}
