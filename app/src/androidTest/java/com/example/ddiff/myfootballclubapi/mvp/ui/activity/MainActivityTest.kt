package com.example.ddiff.myfootballclubapi.mvp.ui.activity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.ddiff.myfootballclubapi.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 * github : ddiffa
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    //jvmfield untuk memberitahu Junit bahwa yang kita beri anotrasi tersebut adalah sebuah properti bukan field
    @Rule // untuk menentukan sebuah rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {

        //cek recylerview
        onView(withId(rv_fragment_match))
                .check(matches(isDisplayed()))
        Thread.sleep(2000)

        //cek swiperefresh
        onView(withId(swipeMatch)).perform(ViewActions.swipeDown())
        Thread.sleep(2000)

        //scroll recycler sampai posisi ke 10
        onView(withId(rv_fragment_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        //click item ke 10
        onView(withId(rv_fragment_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        Thread.sleep(2000)
        //click favorite
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(2000)

        pressBack()

        Thread.sleep(2000)

        onView(withId(menu_next_match)).perform(click())
        onView(withId(rv_fragment_next_match))
                .check(matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withId(swipeNextMatch)).perform(ViewActions.swipeDown())

        Thread.sleep(2000)

        onView(withId(rv_fragment_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rv_fragment_next_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        onView(withId(add_to_favorite)).perform(click())

        Thread.sleep(2000)
        onView(withId(add_to_favorite)).perform(click())

        Thread.sleep(2000)

        pressBack()
        Thread.sleep(2000)

        onView(withId(menu_fav)).perform(click())
        onView(withId(rv_match_favorite))
                .check(matches(isDisplayed()))
        Thread.sleep(2000)

        onView(withId(rv_match_favorite)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(rv_match_favorite)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(2000)
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(2000)
        pressBack()
        onView(withId(swipeFav)).perform(ViewActions.swipeDown())
        Thread.sleep(2000)

    }
}