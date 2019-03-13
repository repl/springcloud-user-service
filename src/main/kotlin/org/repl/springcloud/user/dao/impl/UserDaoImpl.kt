package org.repl.springcloud.user.dao.impl

import org.repl.springcloud.common.dto.ListViewRequestDto
import org.repl.springcloud.common.dto.PaginatedListDto
import org.repl.springcloud.user.dao.UserDao
import org.repl.springcloud.user.dao.UserRepository
import org.repl.springcloud.user.dao.model.UserMdl
import org.repl.springcloud.user.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query


@Repository
class UserDaoImpl : UserDao {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun getAll(): List<UserMdl> {
        return userRepository.findAll()
    }

    override fun getFilteredPaginated(requestDto: ListViewRequestDto): PaginatedListDto<UserDto> {
        if (requestDto.pageSize == 0) {
            requestDto.pageSize = 10
        }
        if (requestDto.pageNum == 0) {
            requestDto.pageNum = 1
        }
        val filterCriteria = Criteria()
        if (requestDto.filters.isNotEmpty()) {
            requestDto.filters.forEach { entry ->
                filterCriteria.and(entry.key).`is`(requestDto.filters.get(entry.value))
            }
        }
        val agg = newAggregation(
                match(filterCriteria),
                //project("User"),
                skip((requestDto.pageNum - 1) * requestDto.pageSize),
                limit(requestDto.pageSize.toLong())
        )
        val results = mongoTemplate.aggregate(agg, "User", UserMdl::class.java)
        val paginatedDto : PaginatedListDto<UserDto> = PaginatedListDto()
        paginatedDto.results = results.mappedResults.map { x -> x.createDto() }
        paginatedDto.pageNum = requestDto.pageNum
        paginatedDto.pageSize = requestDto.pageSize
        paginatedDto.totalCount = 0
        return paginatedDto
    }

    override fun create(inputDto: UserDto): UserDto {
        val model = UserMdl()
        model.populate(inputDto)
        val persistedModel = userRepository.save(model)
        return persistedModel.createDto()
    }

    override fun getByUserHandle(userHandle: String): UserDto? {
        val matchedUsers = mongoTemplate.find(query(where("userHandle").`is`(userHandle)), UserMdl::class.java)
        if (matchedUsers.size == 0) {
            return null
        }
        return matchedUsers[0].createDto()
    }

    /*
    @Override
    public int updateDomain(String domain, boolean displayAds) {

        Query query = new Query(Criteria.where("domain").is(domain));
        Update update = new Update();
        update.set("displayAds", displayAds);

        WriteResult result = mongoTemplate.updateFirst(query, update, Domain.class);

        if(result!=null)
            return result.getN();
        else
            return 0;
    }
    */

    override fun getByMobile(mobile: String): UserDto? {
        val matchedUsers = mongoTemplate.find(query(where("mobile").`is`(mobile)), UserMdl::class.java)
        if (matchedUsers.size == 0) {
            return null
        }
        return matchedUsers[0].createDto()
    }

    override fun getByEmailAddress(emailAddress: String): UserDto? {
        val matchedUsers = mongoTemplate.find(query(where("emailAddress").`is`(emailAddress)), UserMdl::class.java)
        if (matchedUsers.size == 0) {
            return null
        }
        return matchedUsers[0].createDto()
    }

    override fun getByUserId(id: String): UserDto? {
        val matchedUsers = mongoTemplate.find(query(where("id").`is`(id)), UserMdl::class.java)
        if (matchedUsers.size == 0) {
            return null
        }
        return matchedUsers[0].createDto()
    }
}