import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import groovy.json.JsonSlurper as JsonSlurper
import groovy.json.JsonBuilder


/*
 * berikut adalah consumer Testcase
 */

KeywordLogger log = new KeywordLogger()


def jsonData = GlobalVariable.payloadData 

println("✅ Payload stored into GlobalVariable:")

log.logInfo('after store data : ' + GlobalVariable.payloadData)

def request = findTestObject('request_no_kafka/post_data_user', [('baseURL') : GlobalVariable.baseURL, ('apikey') : GlobalVariable.apikey, ('body') : jsonData])

def response = WS.sendRequest(request)

def responseBody = response.getResponseBodyContent()

def json = new JsonSlurper().parseText(responseBody)

def status_code = WS.getResponseStatusCode(response)

log.logInfo("Status Code: " + status_code)

log.logInfo("Full JSON: " + json.toString())




