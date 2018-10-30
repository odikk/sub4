package tech.devatacreative.submission4.view.mainmenu

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.devatacreative.submission4.R
import tech.devatacreative.submission4.R.id.*

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddToFavoriteTest{
    @Rule
    @JvmField
    var mAddToFavoriteTest = ActivityTestRule(MainActivity::class.java)

    @Test
    fun AddToFavoriteTest(){

        onView(withId(R.id.navigation_next_match)).check(matches(isDisplayed()))
                onView(withId(navigation_next_match)).perform(click())
        Thread.sleep(5000)
        onView(withId(rv_match_list)).check(matches(isDisplayed()))
        onView(withText("Leicester")).perform(click())
        Thread.sleep(5000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(5000)

    }
}