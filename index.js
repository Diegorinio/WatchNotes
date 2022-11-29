let host = require('./host.js')
const fs = require('fs');
const express = require("express");
const path = require('path');
const app = express();
const router = express.Router();
const port = 8080;
const db = "database.json";

app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use('/', router);
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'))
app.use(express.static(__dirname + '/public'))

app.get('/', (req, res)=>{
    fs.readFile(db,'utf-8',(err, data)=>{
        if(err){res.send(err)}
        let data_parse = JSON.parse(data);
        let respond = JSON.stringify(data_parse).replace(/\\/g, "");
        res.render('index', {data: data_parse.notes});
    })
})

app.post('/addtodb', (req, res)=>{
    let note_title = req.body.note_title;
    let _note_content = req.body.note_content;
    let _creator = req.body.creator;
    let _subject = req.body.subject;
    let tojson = {subject:_subject,
    note_title:note_title,note_content:_note_content,creator:_creator}
    console.log(tojson);
    fs.readFile(db, 'utf-8',(err, data)=>{
        let datajson = JSON.parse(data);
        datajson.notes.push(tojson);
        console.log(datajson.notes);
         fs.writeFile("database.json", JSON.stringify(datajson), 'utf-8', (err,res)=>{
            if(err){res(err)}
         });
    })
    res.redirect('/');
})

app.get('/note', (req, res)=>{
    fs.readFile(db,'utf-8',(err, data)=>{
        if(err){res.send(err)}
        let data_parse = JSON.parse(data);
        let respond = JSON.stringify(data_parse).replace(/\\/g, "");
        res.send(respond)
    })
})
app.listen(port, host.host, ()=>{
    console.log(`Server is running on ${host.host}:${port}`);
    console.log(`Set http://${host.host}:${port}/note in your app`);
    console.log(`Go to http://${host.host}:${port} in your browser`);
})