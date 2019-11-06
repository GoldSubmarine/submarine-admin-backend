module.exports = {
    db: {
        host: '192.168.39.36',
        user: 'root',
        password: 'root',
        database: 'submarine'
    },
    mixin: {
        frontRootPath: '.',
        backRootPath: 'C:/Users/vinson/Desktop/project/java/submarine-admin-backend',
        module: 'system',
        entity: '',
        Entity: '',
        entityZh: ''
    },
    fields: [
        {
            title: '列表展示',
            name: 'isTableShow',
            default: true,
            type: 'checkbox',
            width: '80'
        },
        {
            title: '表单展示',
            name: 'isFormShow',
            default: true,
            type: 'checkbox',
            width: '80'
        },
        {
            title: '前端搜索',
            name: 'isFrontSearch',
            default: false,
            type: 'checkbox',
            width: '80'
        },
        {
            title: '非空',
            name: 'isNotNull',
            default: true,
            type: 'checkbox',
            width: '80'
        },
        {
            title: '表单类型',
            name: 'formType',
            default: 'input',
            type: 'select',
            options: [
                { label: '', value: '' },
                { label: '单行文本框', value: 'input' },
                { label: '多行文本框', value: 'input,textarea' },
                { label: '下拉选择框', value: 'select' },
                { label: '时间选择器', value: 'timeSelect' },
                { label: '日期选择器', value: 'datePicker,date' },
                { label: '日期范围选择器', value: 'datePicker,daterange' },
                { label: '日期时间选择器', value: 'datePicker,datetime' },
                { label: '日期时间范围选择器', value: 'datePicker,datetimerange' },
                { label: '树形选择框', value: 'select,tree' },
                { label: '级联选择器', value: 'cascader' },
                { label: 'switch 开关', value: 'switch' },
                { label: 'slider 滑块', value: 'slider' },
                { label: 'radio 单选框', value: 'radio' },
                { label: 'checkbox 多选框', value: 'checkbox' }
            ]
        },
        // {
        //   title: '字典类型',
        //   name: 'dicType',
        //   default: '',
        //   type: 'select',
        //   options: selectListAll
        // },
        {
            title: 'sql',
            name: 'sqlType',
            default: '=',
            type: 'select',
            options: [
                { label: '=', value: '=' },
                { label: '!=', value: '!=' },
                { label: '>', value: '>' },
                { label: '>=', value: '>=' },
                { label: '<', value: '<' },
                { label: '<=', value: '<=' },
                { label: 'BETWEEN', value: 'BETWEEN' },
                { label: 'LIKE', value: 'LIKE' }
            ]
        },
        {
            title: '校验类型',
            name: 'verifyType',
            default: '',
            type: 'select',
            options: [
                { label: '', value: '' },
                { label: '数字', value: 'number' },
                { label: '整数', value: 'integer' },
                { label: '整数(大于0)', value: 'integerGtZero' },
                { label: '整数(大于等于0)', value: 'integerGtEqZero' },
                { label: '小数(大于0)', value: 'floatGtZero' },
                { label: '小数(大于等于0)', value: 'floatGtEqZero' },
                { label: '邮箱', value: 'email' },
                { label: '电话', value: 'phone' },
                { label: 'ip', value: 'ip' }
            ]
        },
        {
            title: '字符长度',
            name: 'strLenRange',
            default: '',
            type: 'input',
            width: '140',
            placeholder: 'min,max'
        },
        {
            title: '数字范围',
            name: 'numRange',
            default: '',
            type: 'input',
            width: '140',
            placeholder: 'min,max'
        }
    ],
    input: {
        dir: './templates/'
    },
    output: [
        // ==============frontend==============
        {
            template: 'form.ejs',
            path: '<%= frontRootPath %>/src/views/<%= module %>/<%= entity %>/form.vue'
        },
        {
            template: 'index.ejs',
            path: '<%= frontRootPath %>/src/views/<%= module %>/<%= entity %>/index.vue'
        },
        {
            template: 'api.ejs',
            path: '<%= frontRootPath %>/src/api/<%= entity %>.js'
        },
        // ==============backend==============
        {
            template: 'controller.ejs',
            path: '<%= backRootPath %>/src/main/java/org/javahub/submarine/modules/<%= module %>/controller/<%= Entity %>Controller.java'
        },
        {
            template: 'service.ejs',
            path: '<%= backRootPath %>/src/main/java/org/javahub/submarine/modules/<%= module %>/service/<%= Entity %>Service.java'
        },
        {
            template: 'mapper.ejs',
            path: '<%= backRootPath %>/src/main/java/org/javahub/submarine/modules/<%= module %>/mapper/<%= Entity %>Mapper.java'
        },
        {
            template: 'entity.ejs',
            path: '<%= backRootPath %>/src/main/java/org/javahub/submarine/modules/<%= module %>/entity/<%= Entity %>.java'
        },
        {
            template: 'dto.ejs',
            path: '<%= backRootPath %>/src/main/java/org/javahub/submarine/modules/<%= module %>/dto/<%= Entity %>Dto.java'
        },
        {
            template: 'xml.ejs',
            path: '<%= backRootPath %>/src/main/java/org/javahub/submarine/modules/<%= module %>/xml/<%= Entity %>Mapper.xml'
        },
        {
            template: 'mapStruct.ejs',
            path: '<%= backRootPath %>/src/main/java/org/javahub/submarine/modules/<%= module %>/mapstruct/<%= Entity %>MapStruct.java'
        }
    ]
}
