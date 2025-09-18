
// TASK5 : 기존 TASK4.jsx 이어 useEffect/axios를 활용해서 spring+mybatis 서버 와 통신하여 TASK5 완성(등록/전체조회/삭제)하시오.

export default function Task5( props ){
    const [ name , setName ] = useState('')
    const [ phone , setPhone ] = useState('')
    const [ age , setAge ] = useState('')
    const [ members , setMembers ] = useState( [ ] );
    const onAdd = ( ) => {
        const obj = { name , phone , age }
        members.push( obj );
        setMembers( [...members] )
    }
    const onDelete = ( deletePhone )=>{ console.log( deletePhone );
        const newMembers = members.filter( (m)=> { return m.phone != deletePhone ; })
        setMembers( [ ...newMembers ] );
    }
    return (<>
        성명 : <input value={ name } onChange={ (e)=>{ setName(e.target.value ) } } />
        연락처 : <input value={ phone } onChange={ (e)=>{ setPhone( e.target.value) } }/>
        나이 : <input value={ age } onChange={ (e)=>{ setAge( e.target.value ) } } />
        <button onClick={ onAdd }> 등록 </button> <br/>
        {   members.map( ( m ) => {
                return <div>
                        { m.name } { m.phone } { m.age }
                        <button onClick={ ()=> { onDelete( m.phone ) }  }> 삭제 </button>
                    </div>
            })
        }
    </>)
}
