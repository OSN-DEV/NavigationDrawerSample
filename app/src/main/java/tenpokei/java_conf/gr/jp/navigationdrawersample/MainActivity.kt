package tenpokei.java_conf.gr.jp.navigationdrawersample

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast

// toolbar を使うパターン
class MainActivity : AppCompatActivity(), SideMenuFragment.OnFragmentInteractionListener {
    private lateinit var _drawer: DrawerLayout
    private lateinit var _drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // サイドメニュー用のフラグメントを設定
        var fragment: Fragment = SideMenuFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.side_menu_container, fragment)
        transaction.commit()

        // toolbarをActionbarとして設定する
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        _drawer = findViewById(R.id.drawer)
        _drawerToggle = ActionBarDrawerToggle(this, _drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        _drawer.addDrawerListener(_drawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Navigation Drawerを表示する ← のアイコンを表示
        supportActionBar?.setHomeButtonEnabled(true) // 前に戻るの < アイコンの表示だと思う。トップ画面では設定しても意味が無いかもしれない
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // ナビゲーションのボタンの状態を同期するらしい。
        // このメソッドをコールしないと、アイコンの初期表示が ←(戻る) になる。
        _drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        // 内部的にはホームに戻るやナビゲーションバーのボタンの状態を同期するらしい
        _drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onFragmentInteraction(no: Int) {
        _drawer.closeDrawer(Gravity.LEFT)
        when (no) {
            1, 2 -> {
                Toast.makeText(this, "button " + no, Toast.LENGTH_SHORT).show()
            }
            3 -> {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (_drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
