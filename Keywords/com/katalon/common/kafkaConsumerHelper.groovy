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
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import groovy.json.JsonSlurper

import internal.GlobalVariable

public class kafkaConsumerHelper {

	def consumeMessage(String topic, String bootstrapServers, String groupId) {

		Properties props = new Properties()
		props.put("bootstrap.servers", bootstrapServers)
		props.put("group.id", groupId)
		props.put("enable.auto.commit", "true")
		props.put("auto.commit.interval.ms", "1000")
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
		props.put("auto.offset.reset", "earliest")

		def consumer = new KafkaConsumer<String, String>(props)

		consumer.subscribe([topic])

		def records = consumer.poll(Duration.ofSeconds(5))

		def lastRecord = records.last()
	}
}
