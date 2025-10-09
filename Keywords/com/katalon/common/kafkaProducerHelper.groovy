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
//import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

import internal.GlobalVariable

public class kafkaProducerHelper {

	static def sendMessage(String topic, String bootstrapServers, Map userdata) {

		Properties props = new Properties()
		props.put("bootstrap.servers", bootstrapServers)
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

		def producer = new KafkaProducer<String, String>(props)

		def jsonData = JsonOutput.toJson(userdata)

		def record = new ProducerRecord<String, String>(topic, jsonData)

		producer.send(record)

		println "✅ Sent to topic '${topic}': ${jsonData}"

		producer.flush()
		
		producer.close()
	}
}

