package tenpokei.java_conf.gr.jp.navigationdrawersample

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.view.View

class MainActivity2 : AppCompatActivity() , SideMenuFragment.OnFragmentInteractionListener {
    // Actionbar を使うパターン
    private lateinit var _drawer: DrawerLayout
    private lateinit var _drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var fragment : Fragment = SideMenuFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.side_menu_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        _drawer = findViewById<DrawerLayout>(R.id.drawer).apply {
            // Set a simple drawable used for the left or right shadow.
            setDrawerShadow(R.drawable.drawer_shadow, Gravity.START)
        }
        actionBar.run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }

        _drawerToggle = object : ActionBarDrawerToggle(
                this,
                _drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            override fun onDrawerClosed(drawerView: View) {
                actionBar?.title = "close";
                invalidateOptionsMenu() // Creates call to onPrepareOptionsMenu().
            }

            override fun onDrawerOpened(drawerView: View) {
                actionBar?.title = "open"
                invalidateOptionsMenu() // Creates call to onPrepareOptionsMenu().
            }
        }

        _drawer.addDrawerListener(_drawerToggle)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // このメソッドをオーバーライドしないとアクションバーのボタンタップで
        // Navigation Drawerが表示されない(スワイプでは表示出来るけど。。)
        if (_drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // ナビゲーションのボタンの状態を同期するらしい。
        // このメソッドをコールしないと、アイコンが ←(戻る) のままになる。
        _drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        // 内部的にはホームに戻るやナビゲーションバーのボタンの状態を同期するらしい
        _drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onFragmentInteraction(no: Int) {
        this.finish()
    }


//

}
