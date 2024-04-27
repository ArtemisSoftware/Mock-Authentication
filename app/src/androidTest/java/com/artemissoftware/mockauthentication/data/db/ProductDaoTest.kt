package com.artemissoftware.mockauthentication.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ProductDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var appDataBase: AppDataBase
    private lateinit var productDao: ProductDao

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        productDao = appDataBase.getProductDao()
    }

    @Test
    fun insertProduct_returnsSingleProduct() = runTest {
        val product = ProductEntity("", "", 1, "", 12.33, "Test Product")
        productDao.addProducts(listOf(product))
        val result = productDao.getProducts()
        Assert.assertEquals(1, result.first().size)
    }

    @After
    fun closeDatabase() {
        appDataBase.close()
    }
}
