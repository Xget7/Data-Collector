import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.sublimetech.supervisor.domain.services.UploadFormsJobService
import com.sublimetech.supervisor.domain.services.UploadVisitJobService


class OfflineUpdatesReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val womanAndYouthJob =
                JobInfo.Builder(1, ComponentName(context, UploadFormsJobService::class.java))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build()
            val familyJob =
                JobInfo.Builder(2, ComponentName(context, UploadVisitJobService::class.java))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build()

            if (networkInfo != null && networkInfo.isConnected) {
                // Aquí se llama al método que se encarga de subir las imágenes
                jobScheduler.schedule(womanAndYouthJob)
                jobScheduler.schedule(familyJob)
                Log.d("OfflineUpdatesReceiver", "networkInfo != null && networkInfo.isConnected) ")
            }
        }
    }


}
