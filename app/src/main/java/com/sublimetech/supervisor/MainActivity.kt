package com.sublimetech.supervisor


import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sublimetech.supervisor.data.local.dao.FormsDao
import com.sublimetech.supervisor.data.local.dao.VisitDao
import com.sublimetech.supervisor.domain.services.UploadFormsJobService
import com.sublimetech.supervisor.domain.services.UploadVisitJobService
import com.sublimetech.supervisor.presentation.utils.Utils.ADMIN
import com.sublimetech.supervisor.presentation.utils.Utils.PROFESSIONAL
import com.sublimetech.supervisor.presentation.utils.Utils.SUPERVISOR
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @Inject
    lateinit var formDb: FormsDao

    @Inject
    lateinit var familyDb: VisitDao


    lateinit var jobScheduler: JobScheduler

    lateinit var womanAndYouthJob: JobInfo


    lateinit var familyJob: JobInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);


        if (savedInstanceState == null) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            when (intent.getStringExtra("userType")) {
                ADMIN -> {

                }

                PROFESSIONAL -> {
                    val b = Bundle()
                    Log.d("professionNav", intent.getStringExtra("profession").toString())
                    b.putString("professional", intent.getStringExtra("profession"))
                    navController.navigate(R.id.professionalFragment, b)
                }

                SUPERVISOR -> {
                }
            }
        } else {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            navController.restoreState(savedInstanceState.getBundle("nav_state"))

        }


        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        womanAndYouthJob =
            JobInfo.Builder(3, ComponentName(this, UploadFormsJobService::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build()
        familyJob = JobInfo.Builder(4, ComponentName(this, UploadVisitJobService::class.java))
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()


        listenDatabase()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle("nav_state", navController.saveState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        navController.restoreState(savedInstanceState.getBundle("nav_state"))
    }


    private fun listenDatabase() {
        // Escuchar cambios en la base de datos 'formDb'
        lifecycleScope.launch {
            formDb
                .getForms()
                .distinctUntilChanged()
                .collect {
                    Log.d("listenDatabase", "Form database changed")
                    if (jobScheduler.getPendingJob(1) == null) {
                        Log.d("listenDatabase", "NetworkService null")
                        jobScheduler.schedule(womanAndYouthJob)
                    }
                }
        }

        // Escuchar cambios en la base de datos 'familyDb'
        lifecycleScope.launch {
            familyDb
                .getAllVisits()
                .distinctUntilChanged()
                .collect {
                    Log.d("listenDatabase", "Family database changed")
                    if (jobScheduler.getPendingJob(2) == null) {
                        Log.d("listenDatabase", "Family database changed")
                        jobScheduler.schedule(familyJob)
                    }

                }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
