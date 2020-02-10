<template>
    <div class="select_invoice">
        <el-upload
                class="upload-demo"
                :multiple="true"
                :action="uploadPath"
                :data="uploadData"
                accept="bmg,.png,.jpg,.jpeg"
                :before-upload="beforeUpload"
                :on-success="handleSuccess"
                :on-error="hanldeError"
                multiple
                :limit="20">
            <el-button size="small" type="primary">添加图片</el-button>
        </el-upload>
    </div>
</template>
<script>
    // 引入image-conversion
    import imageConversion from 'image-conversion'
    export default {
        methods: {
            // 第一种,不考虑图片长宽,只考虑图片大小的情况,图片超过4M就压缩
            beforeUpload (file) {
                return new Promise((resolve, reject) => {
                    let isLt2M = file.size / 1024 / 1024 < 4 // 判定图片大小是否小于4MB
                    if (isLt2M) {
                        resolve(file)
                    }
                    console.log(file)
                    // 压缩到400KB,这里的400就是要压缩的大小,可自定义
                    imageConversion.compressAccurately(file, 400).then(res => {
                        resolve(res)
                    })
                })
            },
            //第二种,图片大小超过4M,长度超过2000就压缩
            beforeUpload2 (file) {
                // 图片不大于4m,宽度不大于2000
                return new Promise((resolve, reject) => {
                    let _URL = window.URL || window.webkitURL
                    let isLt2M = file.size / 1024 / 1024 > 4 // 判定图片大小是否小于4MB
                    // 这里需要计算出图片的长宽
                    let img = new Image()
                    img.onload = function () {
                        file.width = img.width // 获取到width放在了file属性上
                        file.height = img.height // 获取到height放在了file属性上
                        let valid = img.width > 2000 // 图片宽度大于2000
                        // console.log(11, file)
                        // 这里我只判断了图片的宽度,compressAccurately有多个参数时传入对象
                        if (valid || isLt2M) {
                            imageConversion.compressAccurately(file, {
                                size: 400,
                                width: 2000
                            }).then(res => {
                                // console.log(33, res)
                                resolve(res)
                            })
                        } else resolve(file)
                    }
                    // 需要把图片赋值
                    img.src = _URL.createObjectURL(file)
                })
            },
        }
    }

</script>
<style scoped>

</style>
