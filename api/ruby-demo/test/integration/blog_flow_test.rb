require 'test_helper'

class BlogFlowTest < ActionDispatch::IntegrationTest
  test 'can see the welcome page' do
    get '/'
    assert_select 'h1', 'Hello, Rails!'
  end

  test 'can create an article' do
    get '/articles/new'
    assert_response :success

    post '/articles',
         params: { article: {title: 'can create', text: 'article successfully.'} }
    assert_response :redirect
    follow_redirect!
    assert_response :success
    assert_select 'p', "Title:\n  can create"
    assert_select 'h2', 'Comments'
    assert_select 'h2', 'Add a comment:'

    get '/articles'
    assert_response :success
    assert_select 'td', 'can create'
  end
end
