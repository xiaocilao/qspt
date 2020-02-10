<template>
  <div class="select-city" ref="selectCity">
    <el-cascader
      :options="options2"
      @change="change"
      v-model="selCity"
      :props="props"
    ></el-cascader>
  </div>

</template>

<style>
  .select-city .el-input{
    width: 350px;
  }
</style>

<script>
  import addressData from 'common/json/class4new.json'
  export default {
    props: {
      value: {
        required: true
      },
      getCityName: {

      }
    },
    data() {
      return {
        options2: addressData,
        props: {
          label: 'name',
          value: 'id',
          children: 'children'
        },
        selCity: []
      }
    },
    watch: {
      value (val) {
        this.init()
      }
    },
    created() {
      // 组件刚载入并不会触发watch value
    },
    methods: {
      init() {
        let el = this.$refs.selectCity
        if (!this.value) {
          if (this.selCity.length) {
            this.selCity = []
            el.getElementsByClassName('el-cascader__label')[0].innerHTML = ''
            el.getElementsByClassName('el-input__inner')[0].setAttribute('placeholder', '请选择')
          }
        } else {
          if (this.selCity.length===0 || this.selCity[3] !== this.value) {
            this.selCity[0] = this.value.substr(0, 2) + '0000'
            this.selCity[1] = this.value.substr(0, 4) + '00000000'
            this.selCity[2] = this.value.substr(0, 6) + '000000'
            this.selCity[3] = this.value
            let name = this.getNode().join('<span>/</span>')
            el.getElementsByClassName('el-cascader__label')[0].innerHTML = name
            el.getElementsByClassName('el-input__inner')[0].setAttribute('placeholder', '')
          }
        }
      },
      change(val) {
        // 只有选完了，才会将数据返回给父组件
        this.$emit('input', val[3])
        this.returnCityName()
      },
      returnCityName() {
        if (typeof this.getCityName === 'function') {
          this.getCityName(this.getNode().join(''))
        }
      },
      getNode() {
        let name = []
        this.options2.filter(v => {
          if (name[0]) return
          if (v.id===this.selCity[0]) {
            name.push(v.name)
            v.children.filter(v => {
              if (name[1]>0) return
              if (v.id===this.selCity[1]) {
                name.push(v.name)
                v.children.filter(v => {
                  if (name[2]>0) return
                  if (v.id===this.selCity[2]) {
                    name.push(v.name)
                    v.children.filter(v => {
                      if (name[3]>0) return
                      if (v.id===this.selCity[3]) {
                        name.push(v.name)
                        return false
                      }
                    })
                  }
                })
              }
            })
          }
        })
        return name
      }
    }
  }
</script>
