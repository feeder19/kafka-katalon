package com.katalon.common

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import static java.lang.System.exit
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig
import groovy.transform.CompileStatic as CompileStatic
import io.confluent.kafka.serializers.KafkaJsonDeserializer as KafkaJsonDeserializer
import org.apache.kafka.clients.consumer.KafkaConsumer as KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer as StringSerializer
import org.apache.kafka.common.serialization.StringDeserializer as StringDeserializer
import java.time.Duration as Duration
import java.util.Properties as Properties

public class Keyword_kafka {


	/**
	 * Create new KafkaConsumer
	 * @param topic name of topic from which to consume
	 * @return a KafkaConsumer ready to consume from topic
	 */
	@Keyword
	def createKafkaConsumer() {
		// Load properties from disk.
		Properties config = new Properties()

		// Add additional properties.
		(config[BOOTSTRAP_SERVERS_CONFIG]) = GlobalVariable.kafka_bootstrap_servers

		(config[GROUP_ID_CONFIG]) = GlobalVariable.kafka_group_id

		(config[KEY_DESERIALIZER_CLASS_CONFIG]) = StringDeserializer.name

		(config[VALUE_DESERIALIZER_CLASS_CONFIG]) = StringDeserializer.name

		(config[AUTO_OFFSET_RESET_CONFIG]) = GlobalVariable.kafka_auto_offset_reset

		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1) // Setting the maximum poll records to 1

		def consumer = new KafkaConsumer<String, String>(config)

		return consumer
	}

	/**
	 * Create new KafkaProducer
	 * @return a KafkaProducer ready to produce to a topic
	 */
	@Keyword
	def createKafkaProducer() {
		// Load properties from disk.
		Properties config = new Properties()

		// Add additional properties.
		config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = GlobalVariable.kafka_bootstrap_servers
		config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer.name
		config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer.name
		config[ProducerConfig.ACKS_CONFIG] = GlobalVariable.kafka_acks // You might want to make this configurable

		def producer = new KafkaProducer<String, String>(config)

		return producer
	}


	/**
	 * Subscribe consumer to topic
	 * @param consumer KafkaConsumer
	 * @param topic name of topic from which to consume
	 * @return a KafkaConsumer ready to consume from topic
	 */
	@Keyword
	def subscribe(KafkaConsumer consumer, String topic) {
		consumer.subscribe((([topic]) as List<String>))

		return consumer
	}

	/**
	 * Fetch a record from a consumer.
	 * @param consumer KafkaConsumer
	 * @param topic name of topic from which to consume
	 * @return a record of some type
	 */
	@Keyword
	def fetch(KafkaConsumer consumer) {
		def record = null

		consumer.withCloseable({
			def records = consumer.poll(Duration.ofMillis(10000))

			for (def r : records) {
				record = r
				break
			}
		})

		return record
	}

	/**
	 * Produce a record to a Kafka topic
	 * @param producer KafkaProducer
	 * @param topic name of topic to which to produce
	 * @param key the key of the record
	 * @param value the value of the record
	 * @return metadata related to the produced record
	 */
	@Keyword
	def produce(KafkaProducer<String, String> producer, String topic, String key, String value) {
		def record = new ProducerRecord<String, String>(topic, key, value)
		def metadata = producer.send(record).get() // Synchronous send; consider handling exceptions

		return metadata
	}
}
