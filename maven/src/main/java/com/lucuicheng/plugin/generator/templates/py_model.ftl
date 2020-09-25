class ${className}(db.Model):
    __tablename__ = '${tableName}'
    __table_args__ = {'extend_existing': True}
<#list fields as a>
    <#if a.key?? && a.key == "PRI">
    id = db.Column(db.Integer, primary_key=True)
    <#else>
    ${a.column} = db.Column(db.${a.sqlAlchemyType}, unique=True, nullable=False) # ${a.comment}
    </#if>
</#list>