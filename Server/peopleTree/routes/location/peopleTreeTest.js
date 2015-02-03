var express = require('express');
var router = express.Router();

router.get('/insertNode', function(req, res) {

	var userNumber = req.query.userNumber;
	peopleTree.insertNode(userNumber,function(res){
		console.log(res);
	});
    res.json("welcom to Location Compare API");
});

router.get('/getItems', function(req, res) {

	var groupMemberId = req.query.groupMemberId;

	peopleTree.getItems(groupMemberId,function(err,obj){

		console.log(JSON.stringify(obj));
		res.json(obj);
		
	});
   
});


router.get('/updateLocation', function(req, res) {

	peopleTree.updateLocation(11,2.1111,3.1111,function(err,obj){

		console.log("/updateLocation : "+obj);
		
	});

    res.json("welcom to Location updateLocation API");
});


router.get('/deleteNode', function(req, res) {

	var userNumber = req.query.userNumber;
	peopleTree.deleteNode(userNumber,function(err,obj){

		if(!err){
			res.json(obj);
		}
		else{
			console.log("err");
			res.json(err);
		}
	});
});

router.get('/isRoot', function(req, res) {

	peopleTree.isRoot(11,function(err,obj){

		console.log("/isRoot : "+ obj);
		
	});

    res.json("welcom to Location updateLocation API");
});


router.get('/changeParent', function(req, res) {

	var myGroupMemberId = req.query.myGroupMemberId;
	var parentGroupMemberId = req.query.parentGroupMemberId;

	peopleTree.changeParent(myGroupMemberId,parentGroupMemberId,function(err,obj){

		if(!err){
			console.log("/changeParent : "+ JSON.stringify(obj));
			res.json(obj);
		}
		else{
			res.json(err);
		}
	});
});

router.get('/showTree', function(req, res) {

	var rootGroupMemberId = req.query.rootGroupMemberId;
	/*
	var Tree = {id : 1, children:[]};
	var position = Tree.children;
	position.push({id : 1, children:[]});
	console.log(position.length-1);
	position = position[position.length-1].children;
	position.push({id : 3, children:[]});
	position[this.length-1].children.push({id : 4, children:[]});
	*/

	peopleTree.isExist(rootGroupMemberId, function(err,flag){
		if(!err){
			if(flag){
				global.callNumber = 0;
				global.treeJson = [{id : rootGroupMemberId, children:[]}];
				var position = treeJson;
				callNumber++;
				console.log("root callNumber1 : "+ callNumber);
				peopleTree.showTree(rootGroupMemberId ,position, 0, function(obj){
					console.log("root callNumber2 : "+ callNumber);
					if(callNumber==0) res.json(treeJson);
				});
			}
			else{
				res.json({state:200,errorDesc:"not exist groupMemberId"});
			}
		}
		else{
			res.json({state:500,errorDesc:"redis error"});
		}
	});
});

module.exports = router;