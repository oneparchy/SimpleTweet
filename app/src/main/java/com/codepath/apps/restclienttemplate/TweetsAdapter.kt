package com.codepath.apps.restclienttemplate

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet
import kotlin.random.Random

class TweetsAdapter(private val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //Inflate item layout
        val view = inflater.inflate(R.layout.item_tweet, parent, false)
        /*   //test code to add a random background color to each Tweet
        val androidColors = Resources.getSystem().getIntArray(R.array.androidcolors)
        val randomAndroidColor = androidColors[Random(androidColors.size).nextInt()]
        view.setBackgroundColor(randomAndroidColor)
        */
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //populate data into the item through the viewholder

        //Get data model based on position
        val tweet: Tweet = tweets.get(position)

        //set item views based on views and data model
        holder.tvUsername.text = tweet.user?.name       //add null safety check in case user was not parsed properly
        holder.tvTweetBody.text = tweet.body
        holder.tvTweetAge.text = tweet.getFormattedTimestamp()
        //use glide to load the profile image into the image view
        Glide.with(holder.itemView).load(tweet.user?.publicImageURL).into(holder.ivProfileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    // Clean all elements of the recycler
    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val  tvUsername = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
        val tvTweetAge = itemView.findViewById<TextView>(R.id.tvTweetAge)
    }
}