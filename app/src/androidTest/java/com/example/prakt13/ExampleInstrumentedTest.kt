package com.example.prakt13

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.DelicateCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

fun test(anime: Anime): Matcher<View> {
    return object: BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("with anime ${anime.anime}, ${anime.character}, ${anime.quote}")
        }

        override fun matchesSafely(item: RecyclerView?): Boolean {
            val listStorage = (item?.adapter as AnimeAdapter).getList()
            val result = listStorage.any{it.anime == anime.anime && it.character == anime.character && it.quote == anime.quote}
            return result
        }
    }
}

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.prakt13", appContext.packageName)
    }

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)
    @Test
    fun test() {
        onView(withId(R.id.editTextCharacter)).perform(ViewActions.typeText("Saitama"))
        onView(withId(R.id.buttonFind)).perform(ViewActions.click())
        val animeStorage = Anime("One Punch Man", "Saitama",
            "Prophecies don't ever come true.")
        Thread.sleep(10000)
        onView(withId(R.id.recyclerViewQuotes)).check(matches(test(animeStorage)))
    }

    @DelicateCoroutinesApi
    @Test
    fun test1() {
        Thread.sleep(1000)
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.recyclerViewQuotes)
        val itemCount =
            Objects.requireNonNull(recyclerView.adapter!!).itemCount
        onView(ViewMatchers.withId(R.id.recyclerViewQuotes))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            ).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }
}