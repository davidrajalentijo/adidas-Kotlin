package com.example.rajadav.adidas_kotlin.ui.goals

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

import com.example.rajadav.adidas_kotlin.MainViewModel
import com.example.rajadav.adidas_kotlin.R
import com.example.rajadav.adidas_kotlin.ViewModelFactory
import com.example.rajadav.adidas_kotlin.model.CompletedGoal
import com.example.rajadav.adidas_kotlin.model.Goal
import com.example.rajadav.adidas_kotlin.model.Reward

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.*
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.activity_detail.view.*

/*This Activity show one goal with detail and connect with Google Fit to receive the corresponding data*/
class DetailActivity : AppCompatActivity() {

    private lateinit var model: MainViewModel
    private var mListener: OnDataPointListener? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.extras.getInt("id")

        val factory = ViewModelFactory(this)
        model = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        val fitnessOptions: FitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .build()

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions)
        } else {
            getGoal(id)
        }
    }

    fun getGoal(id: Int) {
        model.getGoal(id).observe(this, Observer<Goal> {
            tv_title_goal.text = it?.title
            tv_description_goal.text = it?.description
            goal_progressbar.max = it?.goal!!
            accessGoogleFit(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                getGoal(id)
            }
        }
    }

    private fun accessGoogleFit(goal: Goal) {
        val cal: Calendar = Calendar.getInstance()
        val endTime: Long = cal.timeInMillis
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val startTime: Long = cal.timeInMillis

        val builder = DataReadRequest.Builder().setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS).bucketByTime(1, TimeUnit.DAYS)

        if (goal.type.equals("step")) {
            builder.aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
        } else if (goal.type.equals("walking_distance") || goal.type.equals("running_distance")) {
            builder.aggregate(DataType.TYPE_DISTANCE_DELTA, DataType.AGGREGATE_DISTANCE_DELTA)
        }

        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .findDataSources(
                        DataSourcesRequest.Builder().setDataTypes(DataType.TYPE_STEP_COUNT_DELTA, DataType.TYPE_DISTANCE_DELTA)
                                .setDataSourceTypes(DataSource.TYPE_DERIVED).build()
                ).addOnSuccessListener {
                    for (dataSource: DataSource in it) {
                        if (dataSource.dataType.equals(DataType.TYPE_STEP_COUNT_DELTA) && mListener == null && goal.type.equals("step")) {
                            registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_DELTA, goal)
                        } else if (dataSource.dataType.equals(DataType.TYPE_DISTANCE_DELTA) && mListener == null && (goal.type.equals("walking_distance") || goal.type.equals(("running_distance")))) {
                            registerFitnessDataListener(dataSource, DataType.TYPE_DISTANCE_DELTA, goal)
                        }
                    }
                }.addOnFailureListener {
                    Log.e("DetailActivity", "failed", it);
                }

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(builder.build())
                .addOnSuccessListener { response ->
                    if (response.buckets.size > 0) {
                        for (bucket: Bucket in response.buckets) {
                            var dataSets: List<DataSet> = bucket.dataSets
                            for (dataSet: DataSet in dataSets) {
                                if (dataSet.dataType.name.equals("com.google.step_count.delta")) {
                                    showStepsDataSet(goal, goal.reward, dataSet)
                                } else if (dataSet.dataType.name.equals("com.google.distance.delta")) {
                                    showDistanceDataSet(goal, goal.reward, dataSet)
                                }
                            }
                        }
                    } else if (response.dataSets.size > 0) {
                        for (dataSet: DataSet in response.dataSets) {
                            if (dataSet.dataType.name.equals("com.google.step_count.delta")) {
                                showStepsDataSet(goal, goal.reward, dataSet)
                            } else if (dataSet.dataType.name.equals("com.google.distance.delta")) {
                                showDistanceDataSet(goal, goal.reward, dataSet)
                            }
                        }
                    }
                }.addOnFailureListener { e -> Log.e("err", e.toString()) }
    }

    private fun registerFitnessDataListener(dataSource: DataSource, dataType: DataType, goal: Goal) {

        mListener =
                OnDataPointListener {
                    for (field: Field in it.dataType.fields) {
                        var valor: Value = it.getValue(field)
                        if (dataType.name.equals("com.google.step_count.delta")) {
                            livesteps(valor.asInt(), goal)
                        } else if (dataType.name.equals("com.google.distance.delta")) {
                            var a: Float = valor.asFloat()
                            var b: Int = Math.round(a)
                            livedistance(b, goal)
                        }
                    }
                }

        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .add(
                        SensorRequest.Builder()
                                .setDataSource(dataSource) // Optional but recommended for custom data sets.
                                .setDataType(dataType) // Can't be omitted.
                                .setSamplingRate(10, TimeUnit.SECONDS)
                                .build(),
                        mListener)
                .addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful()) {
                                Log.i("DetailActivity", "Listener registered!")
                            } else {
                                Log.e("DetailActivity", "Listener not registered.", it.getException())
                            }
                        })
    }

    private fun unregisterFitnessDataListener() {
        if (mListener == null) {
            // This code only activates one listener at a time.  If there's no listener, there's
            // nothing to unregister.
            return;
        }

        // [START unregister_data_listener]
        // Waiting isn't actually necessary as the unregister call will complete regardless,
        // even if called from within onStop, but a callback can still be added in order to
        // inspect the results.
        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .remove(mListener)
                .addOnCompleteListener(
                        OnCompleteListener {
                            fun onComplete(@NonNull task: Task<Boolean>) {
                                if (task.isSuccessful() && task.getResult()) {
                                    Log.i("DetailActivity", "Listener was removed!");
                                } else {
                                    Log.i("DetailActivity", "Listener was not removed.");
                                }
                            }
                        }
                )
        // [END unregister_data_listener]
    }

    override fun onStop() {
        super.onStop()
        unregisterFitnessDataListener()
    }

    /* function that receive the steps of Google Fit*/
    fun showStepsDataSet(goal: Goal, reward: Reward, dataSet: DataSet) {

        if (dataSet.dataPoints.size == 0) {
            goal_progressbar.progress = 0
            tv_number_steps_or_distance.text = resources.getString(R.string.detail_steps_done, 0)
        }

        for (dp: DataPoint in dataSet.dataPoints) {
            tv_number_steps_or_distance.text = resources.getString(R.string.detail_steps_done, dp.getValue(Field.FIELD_STEPS).asInt())
            goal_progressbar.progress = dp.getValue(Field.FIELD_STEPS).asInt()
            checkGoalCompleted(goal, reward, dp.getValue(Field.FIELD_STEPS).asInt())
        }
    }

    /* function that receive the distance of Google Fit*/
    fun showDistanceDataSet(goal: Goal, reward: Reward, dataSet: DataSet) {

        if (dataSet.dataPoints.size == 0) {
            goal_progressbar.progress = 0
            tv_number_steps_or_distance.text = resources.getString(R.string.detail_distance_done, 0)
        }

        for (dp: DataPoint in dataSet.dataPoints) {
            var a: Float = dp.getValue(Field.FIELD_DISTANCE).asFloat()
            var b: Int = Math.round(a)
            tv_number_steps_or_distance.text = resources.getString(R.string.detail_distance_done, b)
            goal_progressbar.progress = b
            checkGoalCompleted(goal, reward, b)
        }
    }

    /* function that check if one goal is completed */
    fun checkGoalCompleted(goal: Goal, reward: Reward, value: Int) {

        if (value >= goal.goal) {
            when (reward.trophy) {
                Goal.BRONZE_REWARD -> image_goal.setImageResource(R.drawable.bronzemedal)
                Goal.GOLD_REWARD -> image_goal.setImageResource(R.drawable.goldmedal)
                Goal.SILVER_REWARD -> image_goal.setImageResource(R.drawable.silvermedal)
                Goal.ZOMBIE_REWARD -> image_goal.setImageResource(R.drawable.if__zombie_rising_1573300)
            }
            tv_status.visibility = View.VISIBLE
            image_goal.visibility = View.VISIBLE
            points_goal.visibility = View.VISIBLE
            points_goal.text = resources.getString(R.string.detail_points_earned, reward.points)

            val cal: Calendar = Calendar.getInstance()
            val date = Date()
            val newCompleted = CompletedGoal(goal.id, goal.title, reward.points, reward.trophy, cal.get(Calendar.DAY_OF_MONTH), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.YEAR), date.hours, date.minutes, date.seconds)
            model.insertGoalDone(newCompleted)
        }
    }

    /* function that update the steps of Google Fit*/
    fun livesteps(value: Int, goal: Goal) {
        var finalsteps: Int = value + goal.steps
        goal.steps = finalsteps
        model.updateGoal(goal)
        tv_number_steps_or_distance.text = resources.getString(R.string.detail_steps_done, finalsteps)
    }

    /* function that update the distance of Google Fit*/
    fun livedistance(value: Int, goal: Goal) {
        var finaldistance: Int = goal.distance + value
        goal.distance = finaldistance
        model.updateGoal(goal);
        tv_number_steps_or_distance.text = resources.getString(R.string.detail_distance_done, value)
    }

    companion object {
        private const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 100
    }
}