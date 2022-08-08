package com.example.poc2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koushikdutta.async.AsyncServer.LOGTAG
import com.samsung.multiscreen.Client
import com.samsung.multiscreen.Error
import com.samsung.multiscreen.Result
import com.samsung.multiscreen.Service


class SamsungActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = TVsAdapter(this, ::onClick)
    private val appId = "BFKjejAbbY.GlobosatPlay"
    private val channelId = "com.globo.globoid.auth"
    private val event = "auth"

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
        tvApplication.connect(object : Result<Client?> {
            override fun onSuccess(client: Client?) {
                Toast.makeText(this@SamsungActivity, "Conectado a $client", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Error) {
                Toast.makeText(this@SamsungActivity, "Application.connect onError() $error", Toast.LENGTH_SHORT).show()
                if (error.code == 404L) {
                }
            }
        })
        tvApplication.addOnMessageListener(
            event
        ) { message ->
            Toast.makeText(this@SamsungActivity, "Mensagem recebida $message", Toast.LENGTH_SHORT).show()

            Log.d(
                LOGTAG,
                "Application.onMessage() message: $message"
            )
        }
    }
}
