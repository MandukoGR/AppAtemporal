package com.example.appatemporal.framework.view


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityBoletoEspectadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.ConsultarBoletoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_boleto_espectador.*
import kotlinx.android.synthetic.main.activity_contact_info.view.*

/**
 * This file is linked with activity_boleto_espectador.xml
 * This file is in charge of displaying an specific ticket of the user
 *
 * @see activity_boleto_espectador.xml
 *
 * @author Andrés
 *
 * */

class ConsultarBoleto : AppCompatActivity() {
    private lateinit var binding: ActivityBoletoEspectadorBinding
    private var auth = FirebaseAuth.getInstance()
    private val consultarBoletoViewModel: ConsultarBoletoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoletoEspectadorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository(this)
        val userRole = getSharedPreferences("user", Context.MODE_PRIVATE).getString("rol", "").toString()
        // ----------------------------Header------------------------------------

        // Visibility

        if(auth.currentUser == null){
            binding.header.buttonsHeader.visibility = android.view.View.GONE
        }

        // Intents
        binding.header.logoutIcon.setOnClickListener{
            Log.d("Sesion", "Salió")
            auth.signOut()
            val userSharedPref = getSharedPreferences("user", Context.MODE_PRIVATE)
            var sharedPrefEdit = userSharedPref.edit()
            sharedPrefEdit.remove("userUid")
            sharedPrefEdit.clear().apply()
            val intent = Intent(this, CheckIfLogged::class.java)
            startActivity(intent)
        }

        binding.header.supportIcon.setOnClickListener{
            val intent = Intent(this, SupportActivity::class.java)
            startActivity(intent)
        }
        // ----------------------------Navbar------------------------------------


        Log.d("Rol", userRole)

        // Visibility
        if ((userRole == "Espectador" && auth.currentUser != null) || userRole == "") {
            binding.navbar.budgetIcon.visibility = android.view.View.GONE
            binding.navbar.metricsIcon.visibility = android.view.View.GONE
            binding.navbar.budgetText.visibility = android.view.View.GONE
            binding.navbar.metricsText.visibility = android.view.View.GONE
        } else if (userRole == "Ayudante" && auth.currentUser != null) {
            binding.navbar.budgetIcon.visibility = android.view.View.GONE
            binding.navbar.metricsIcon.visibility = android.view.View.GONE
            binding.navbar.budgetText.visibility = android.view.View.GONE
            binding.navbar.metricsText.visibility = android.view.View.GONE
            binding.navbar.eventsIcon.visibility = android.view.View.GONE
            binding.navbar.eventsText.visibility = android.view.View.GONE
            binding.navbar.homeIcon.visibility = android.view.View.GONE
            binding.navbar.homeText.visibility = android.view.View.GONE
        }

        // Intents
        binding.navbar.homeIcon.setOnClickListener {
            if(userRole == "Organizador" && auth.currentUser != null){
                val intent = Intent(this, ActivityMainHomepageOrganizador::class.java)
                startActivity(intent)
            }else {
                val intent = Intent(this, ActivityMainHomepageEspectador::class.java)
                startActivity(intent)
            }
        }

        binding.navbar.eventsIcon.setOnClickListener {
            if(userRole == "Organizador" && auth.currentUser != null) {
                val intent = Intent(this,ActivityMisEventosOrganizador::class.java)
                startActivity(intent)
            } else if (userRole == "Espectador" && auth.currentUser != null) {
                val intent = Intent(this,CategoriasEventos::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, CheckIfLogged::class.java)
                startActivity(intent)
            }
        }

        binding.navbar.budgetIcon.setOnClickListener {
            val intent = Intent(this, ProyectoOrganizador::class.java)
            startActivity(intent)
        }

        binding.navbar.ticketsIcon.setOnClickListener {
            if ((userRole == "Espectador" || userRole == "Organizador") && auth.currentUser != null) {
                val intent = Intent(this, BoletoPorEventoActivity::class.java)
                startActivity(intent)
            } else if (userRole == "Ayudante" && auth.currentUser != null) {
                val intent = Intent(this, RegisterQRView::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, CheckIfLogged::class.java)
                startActivity(intent)
            }
        }

        binding.navbar.metricsIcon.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        binding.ratingbar.visibility = android.view.View.INVISIBLE
        binding.sendBtn.visibility = android.view.View.GONE
        binding.etComment.visibility = android.view.View.GONE
        binding.sendIcon.visibility = android.view.View.GONE
        binding.commentLabel.visibility = android.view.View.INVISIBLE


        val userUid = getSharedPreferences("userUid", Context.MODE_PRIVATE).getString("userUid", "").toString()

        val idEvento = intent.getStringExtra("idEvento")
        val nombre = intent.getStringExtra("nombre")
        val fecha = intent.getStringExtra("fecha")
        val hora = intent.getStringExtra("hora")
        val lugar = intent.getStringExtra("lugar")
        val direccion = intent.getStringExtra("direccion")
        val ciudad = intent.getStringExtra("ciudad")
        val estado = intent.getStringExtra("estado")
        val hashQr = intent.getStringExtra("hashQr")


        /**
         * Opens intent for Share Menu of Installed Apps in the Device. Sends a Default MSG with link with EventID.
         */
        binding.ivShareEvent.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            val nombreEvento = nombre.toString().replace("\\s".toRegex(), "")
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Checa este nuevo evento!! https://rarr.vercel.app/evento/${nombreEvento.lowercase()}")
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, "send to "))
        }


        consultarBoletoViewModel.verifyComment(userUid, idEvento.toString(), repository)
        consultarBoletoViewModel.commentState.observe(this, Observer { commentExistence ->
            if (commentExistence) {
                binding.etComment.visibility = android.view.View.GONE
                binding.sendIcon.visibility = android.view.View.GONE
                binding.commentLabel.visibility = android.view.View.INVISIBLE
            } else {
                consultarBoletoViewModel.getStateTicket(hashQr.toString(),userUid,repository)
                consultarBoletoViewModel.ticketState.observe(this, Observer {
                      if (it == false) {
                          binding.etComment.visibility = android.view.View.VISIBLE
                          binding.sendIcon.visibility = android.view.View.VISIBLE
                          binding.commentLabel.visibility = android.view.View.VISIBLE
                          binding.sendIcon.setOnClickListener {
                              if (!etComment.text.isNullOrEmpty()) {
                                  val etContent = binding.etComment.text.toString()
                                  consultarBoletoViewModel.addComment(userUid,idEvento.toString(),etContent,repository)
                                  Toast.makeText(this, "Tu comentario fue registrado exitosamente!", Toast.LENGTH_SHORT).show()
                                  binding.etComment.getText().clear();
                                  Handler(Looper.myLooper()!!).postDelayed(Runnable{
                                      binding.etComment.visibility = android.view.View.GONE
                                      binding.sendIcon.visibility = android.view.View.GONE
                                      binding.commentLabel.visibility = android.view.View.INVISIBLE

                                  },1000)
                              } else {
                                  Toast.makeText(this, "Por favor llena el campo", Toast.LENGTH_SHORT).show()
                              }
                          }
                      }
                })
            }
        })


        consultarBoletoViewModel.verifyRate(userUid, idEvento.toString(), repository)
        consultarBoletoViewModel.rateState.observe(this, Observer {
            if (it) {
                binding.ratingbar.visibility = android.view.View.INVISIBLE
                binding.sendBtn.visibility = android.view.View.GONE
            } else {
                consultarBoletoViewModel.getStateTicket(hashQr.toString(),userUid,repository)
                consultarBoletoViewModel.ticketState.observe(this, Observer {
                    if (it == false){
                        binding.ratingbar.visibility = android.view.View.VISIBLE
                        binding.sendBtn.visibility = android.view.View.VISIBLE
                        binding.sendBtn.setOnClickListener {
                            consultarBoletoViewModel.addRating(userUid, idEvento.toString(), binding.ratingbar.rating, repository)
                            Toast.makeText(this, "El evento ha sido calificado", Toast.LENGTH_SHORT).show()
                            Handler(Looper.myLooper()!!).postDelayed(Runnable{
                                binding.ratingbar.visibility = android.view.View.INVISIBLE
                                binding.sendBtn.visibility = android.view.View.GONE
                            },1000)
                        }
                    }
                })
            }
        })

        binding.NombreEventoBoleto.text = nombre
        binding.FechaEvento.text = fecha
        binding.LugarEvento.text = lugar
        binding.DireccionEvento.text = direccion
        binding.EstadoEvento.text = ciudad + ", " + estado

        val CODIGO_QR : BarcodeEncoder = BarcodeEncoder()
        val BITMAP: Bitmap =  CODIGO_QR.encodeBitmap(hashQr, BarcodeFormat.QR_CODE, 100, 100)
        var TARJETA_CODIGO_QR: ImageView = findViewById(R.id.CodigoQr)
        TARJETA_CODIGO_QR.setImageBitmap(BITMAP)

    }
}