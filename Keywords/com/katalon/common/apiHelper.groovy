package com.katalon.common

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

public class apiHelper {
	
	static def sendAndReceive(String baseUrl, def payload) {
		def postResponse = WS.sendRequest(findTestObject('POST_CreateUser', [('baseUrl') : baseUrl, ('payload') : JsonOutput.toJson(payload)]))
		assert postResponse.getStatusCode() == 201

		def getResponse = WS.sendRequest(findTestObject('GET_UserById', [('baseUrl') : baseUrl, ('id') : payload.id]))
		assert getResponse.getStatusCode() == 200

		def data = new JsonSlurper().parseText(getResponse.getResponseText())
		return data
	}
	
	
}
