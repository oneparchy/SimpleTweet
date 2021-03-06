package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

private val TAG = "ComposeActivity"
class ComposeActivity : AppCompatActivity() {

    lateinit var etNewTweet: EditText
    lateinit var btnTweet: Button
    lateinit var tvRemChars: TextView
    lateinit var client:TwitterClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etNewTweet = findViewById(R.id.etNewTweet)
        btnTweet = findViewById(R.id.btnTweet)
        tvRemChars = findViewById(R.id.tvRemChars)
        client = TwitterApplication.getRestClient(this)

        //Update Textview to show remaining character count as user types
        val textWatcher = object: TextWatcher {
            //unused
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            //unused
            override fun afterTextChanged(p0: Editable?) {
            }
            //used
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, before: Int) {
                val rc = 279-start+count
                tvRemChars.text = "$rc characters remaining"
            }
        }
        etNewTweet.addTextChangedListener(textWatcher)

        //Handle the user's click on the tweet button
        btnTweet.setOnClickListener {
            //capture the content of the new tweet
            val tweetContent = etNewTweet.text.toString()
            //Ensure the tweet is not blank
            //SnackBar is a good alternative instead of Toasts
            if (tweetContent.isEmpty()) {
                Toast.makeText(this,"Tweet cannot be blank!",Toast.LENGTH_SHORT).show()
            } else
            //Make sure the tweet is within allowed character count
            if (tweetContent.length > 280) {
                Toast.makeText(this,"Tweet is too long!",Toast.LENGTH_SHORT).show()
            } else {

                //send to Twitter API to publish the tweet
                client.publishTweet(tweetContent, object:JsonHttpResponseHandler() {
                    override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                        Log.e(TAG, "Failed to publish tweet", throwable)
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.i(TAG, "Successfully published tweet!")
                        //Send the app back to the timeline activity and refresh
                        val tweet = Tweet.fromJson(json.jsonObject)
                        val i = Intent()
                        intent.putExtra("tweet", tweet)
                        setResult(RESULT_OK, i)
                        finish()
                    }

                } )
            }
        }

    }
}