@EnableKafka
public class KafkaConfig {
    @Value("{spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public kafkaTemplate<String, String> kafkaTemplate() {
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> KafkaListenerContainerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP, "auth-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        ConcurrentKafkaListenerContainerFactory<String,String> factoty = new ConcurrentKafkaListenerContainerFactory<>();
        factoty.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        return factory;
    }
}