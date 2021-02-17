package com.rachellima.cardview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ClickItemContactListener {
    private val rvList: RecyclerView by lazy {
        findViewById(R.id.rv_contacts)
    }

    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initdrawer()
        fetchListContact()
        bindViews()
    }

    fun initdrawer() {
        val drawer = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    private fun getListContacts(): List<Contact> {
        val list = getInstanceSharedPreferences().getString("contacts", "[]")
        val turnsType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list, turnsType)
    }

    private fun fetchListContact() {
        val list = arrayListOf(
            Contact(
                "Rachel Lima",
                "(00) 1234-7890",
                "img.png"
            ),
            Contact(
                "Batman",
                "(00) 1111-7890",
                "img.png"
            ),
        )

        getInstanceSharedPreferences().edit {
            putString("contacts", Gson().toJson(list))
            commit()
        }
    }

    private fun getInstanceSharedPreferences(): SharedPreferences {
        return getSharedPreferences("com.rachellima.cardview.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun updateList() {
        adapter.updateListAdapter(
            getListContacts()
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_1 -> {
                showToast("Exibindo item de menu 1")
                true
            }
            R.id.item_menu_2 -> {
                showToast("Exibindo item de menu 2")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this, ContactDetail::class.java)
        intent.putExtra(ContactDetail.EXTRA_CONTACT, contact)
        startActivity(intent)
    }

}