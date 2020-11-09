const mqtt=require('mqtt');
const endClient=require('./end-client-layer.js')
const client = mqtt.connect('mqtt://broker.hivemq.com')
client.on('connect', () => {
    client.subscribe('sownInfo')
    client.subscribe('sownAssign')
    client.subscribe('sownResponse')
  })
client.on("message",(topic,message)=>{
    console.log("%s %s",topic,message);
})
client.publish('ddoongNoti',JSON.stringify(endClient.warnPublic(1,1,1)));