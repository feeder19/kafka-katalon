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
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import com.katalon.common.kafkaProducerHelper as Producer

KeywordLogger log = new KeywordLogger()

def dataset = TestDataFactory.findTestData('data/Users')

def topic = GlobalVariable.topic

def bootstrap = GlobalVariable.bootstrap

def id = dataset.getValue(1, 2) + new Random().nextInt(9)

def nama = dataset.getValue(2, 2) + new Random().nextInt(99)

def email = new Random().nextInt(999) + dataset.getValue(3, 2)

def userdata = [('id') : id, ('nama') : nama, ('email') : email]

log.logInfo('test userdata : '+ userdata)

Producer.sendMessage(topic, bootstrap, userdata)

