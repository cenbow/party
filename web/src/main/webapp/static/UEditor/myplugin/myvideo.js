UE.commands['uploadci'] = {
    execCommand : function(){
    	typeof uEditorUploadCI == 'function' && uEditorUploadCI(this);
    },
	queryCommandState:function(){	    
	     return 0;           //否则返回-1，告诉编辑器将当前按钮置灰
	}
};