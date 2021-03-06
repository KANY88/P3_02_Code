
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;
import org.junit.Test;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(NAME_ASCENDING)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT;
    private int POSITION_ITEM = 0;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mService;
    private List<Neighbour> neighbourList;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mService = DI.getNewInstanceApiService();
        neighbourList = mService.getNeighbours();
        ITEMS_COUNT = neighbourList.size();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void A_myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void B_myNeighboursList_deleteAction_shouldRemoveItem() throws InterruptedException {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));



    }

    /**
     * Open Activity detail, when click on list element.
     */
    @Test
    public void C_myNeighboursList_onClickItem_shouldOpenDetailActivity() {
        //Given : Start Detail Activity
        //when perform a click on item position
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));
        //Then : we check if textView neigbourhNameTitle in DetailActivity is displayed.
        onView(withId(R.id.itemNameTitle)).check(matches(isDisplayed()));
    }

    /**
     * Check if the name in DetailActivity is the same as the item selected.
     */
    @Test
    public void D_detailNeighbourName_onDetailActivity_isCorrect() {
        Neighbour neighbour = neighbourList.get(POSITION_ITEM);

        //Given : Proper name Textview in detailActivity
        //when : open detailActivity
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));
        //Then : we check if text Name displayed in DetailActivity match with neighbour name.
        onView(withId(R.id.profileName)).check(matches(withText(neighbour.getName())));
    }

    /**
     * Check if favorite list contain items marked as favorite.
     */
    @Test
    public void E_favoritesList_onFavoriteTab_showFavoriteItems() {
        //Given : Favorites list in favorite Tab.

        //when : add1 items in favorite onClick on floating action button.
        onView(allOf(withId(R.id.list_neighbours), isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));
        onView(withId(R.id.flot_Favoris))
                .perform(click());
        pressBack();

        //swipe to favorite Tab.
        onView(withId(R.id.container)).perform(swipeLeft());

        //Then : Check if the number of items in Favorite list is same as the number neighbours we added.
        onView(withId(R.id.list_neighboursFavorite)).check(withItemCount(1));
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite))
                .perform(RecyclerViewActions.actionOnItemAtPosition( 0, new DeleteViewAction()));

    }

    /**
     * When we delete an item in favorite, the item is no more shown
     */
    @Test
    public void F_myNeighboursListFavorite_deleteAction_shouldRemoveItemFromFavorite() {

        // Given : We remove the item in favorite List.

        //add item in favorite.
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM +1, click()));
        onView(withId(R.id.flot_Favoris))
                .perform(click());
        pressBack();
        onView(withId(R.id.container)).perform(swipeLeft());
        //check if list is not empty.
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite)).check(withItemCount(1));

        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite))
                .perform(RecyclerViewActions.actionOnItemAtPosition( 0, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite)).check(withItemCount(0));


    }
}

